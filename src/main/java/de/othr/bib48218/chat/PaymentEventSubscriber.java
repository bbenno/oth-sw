package de.othr.bib48218.chat;

import com.othr.swvigopay.entity.TransferDTO;
import com.othr.swvigopay.exceptions.TransferServiceExternalException;
import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.ServiceType;
import de.othr.bib48218.chat.service.IFMessageService;
import de.othr.bib48218.chat.service.IFPaymentService;
import de.othr.bib48218.chat.service.IFUserService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventSubscriber implements ApplicationListener<PartnerServiceEvent> {

    private static final Pattern transferMessage = Pattern.compile(
        "^/request_transfer (?<amount>[0-9]+(\\.[0-9]*)?) (?<email>[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6})\\s*(?<description>.*)$",
        Pattern.CASE_INSENSITIVE);

    private static final String usage = "/request_transfer <amount> <email of payer> <optional description>";
    private static final String example = "/request_transfer 52.43 rich@person.com";

    private static final ServiceType serviceType = ServiceType.PAYMENT;

    @Value("${partner.service.payment.bot_name}")
    private String botName;

    @Autowired
    private IFPaymentService paymentService;

    @Autowired
    private IFMessageService messageService;

    @Autowired
    private IFUserService userService;

    @Override
    public void onApplicationEvent(PartnerServiceEvent event) {
        if (event.getServiceType() == serviceType) {
            Message message = event.getSource();
            Optional<TransferDTO> dto = convertMessageToTransferDto(message);
            Optional<Bot> serviceBot = userService.getBotByUsername(botName);

            String replyText = dto.map(this::sendTransferRequest)
                .orElse(String.join("\n",
                    "insufficient information about transfer request",
                    "usage: " + usage,
                    "example: " + example
                ));

            serviceBot.ifPresent(bot ->
                messageService.saveMessage(
                    new Message(replyText, message.getChat(), bot, LocalDateTime.now(), message)
                ));
        }
    }

    private Optional<TransferDTO> convertMessageToTransferDto(Message message) {
        Matcher matcher = transferMessage.matcher(message.getText());

        if (matcher.matches()) {
            String payerEmail = matcher.group("email");
            String receiverEmail = ((Person) message.getAuthor()).getEmail();
            BigDecimal amount = new BigDecimal(matcher.group("amount"));
            String description = matcher.group("description");

            return Optional.of(new TransferDTO(payerEmail, receiverEmail, amount, description));
        } else {
            return Optional.empty();
        }
    }

    private String sendTransferRequest(TransferDTO transferDTO) {
        try {
            paymentService.requestTransfer(transferDTO);
            return "request accepted";
        } catch (TransferServiceExternalException e) {
            return e.getMessage();
        }
    }
}
