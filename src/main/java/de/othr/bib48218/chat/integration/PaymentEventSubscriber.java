package de.othr.bib48218.chat.integration;

import com.othr.swvigopay.entity.TransferDTO;
import com.othr.swvigopay.exceptions.TransferServiceExternalException;
import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Chat;
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

/**
 * Handler for {@link PartnerServiceEvent}s with {@link ServiceType#PAYMENT}.
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
class PaymentEventSubscriber implements ApplicationListener<PartnerServiceEvent> {

    /**
     * The message text pattern expected to request a transfer.
     */
    private static final Pattern transferMessage = Pattern.compile(
        "^/request_transfer (?<amount>[0-9]+(\\.[0-9]*)?) (?<email>[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6})\\s*(?<description>.*)$",
        Pattern.CASE_INSENSITIVE);

    /**
     * The usage string.
     */
    private static final String USAGE_TEXT = "/request_transfer <amount> <email of payer> <optional description>";
    /**
     * An usage example.
     */
    private static final String EXAMPLE_TEXT = "/request_transfer 52.43 rich@person.com";

    /**
     * The service type of events to handle.
     */
    private static final ServiceType SERVICE_TYPE = ServiceType.PAYMENT;

    private static final String DESCRIPTION_PREFIX = "&&&chat&&&";

    /**
     * The username of the service bot.
     */
    @Value("${partner.service.payment.bot_name}")
    private String botName;

    @Autowired
    private IFPaymentService paymentService;

    @Autowired
    private IFMessageService messageService;

    @Autowired
    private IFUserService userService;

    /**
     * Handles {@link PartnerServiceEvent}s.
     *
     * @param event the partner service event
     */
    @Override
    public void onApplicationEvent(PartnerServiceEvent event) {
        if (event.getServiceType() == SERVICE_TYPE) {
            Message message = event.getSource();
            Optional<Bot> serviceBot = userService.getBotByUsername(botName);
            String replyText;

            if (!(message.getAuthor() instanceof Person)) {
                replyText = "Chating with service bot is only allowed for natural persons.";
            } else if (((Person) message.getAuthor()).getEmail() == null
                || ((Person) message.getAuthor()).getEmail().isBlank()) {
                replyText = "Please first set your email address to the your matching email of the payment service.";
            } else {
                Optional<TransferDTO> dto = convertMessageToTransferDto(message);

                replyText = dto.map(this::sendTransferRequest)
                    .orElse(String.join("\n",
                        "insufficient information about transfer request",
                        "usage: " + USAGE_TEXT,
                        "example: " + EXAMPLE_TEXT
                    ));
            }

            serviceBot.ifPresent(bot -> {
                preTransferRequestAction(message.getChat(), bot);
                Message reply = new Message(replyText, message.getChat(), bot, LocalDateTime.now(),
                    message);
                messageService.saveMessage(reply);
            });
        }
    }

    private Optional<TransferDTO> convertMessageToTransferDto(Message message) {
        Matcher matcher = transferMessage.matcher(message.getText());

        if (matcher.matches()) {
            String payerEmail = matcher.group("email");
            String receiverEmail = ((Person) message.getAuthor()).getEmail();
            BigDecimal amount = new BigDecimal(matcher.group("amount"));
            String description = DESCRIPTION_PREFIX + matcher.group("description");

            return Optional.of(new TransferDTO(payerEmail, receiverEmail, amount, description));
        } else {
            return Optional.empty();
        }
    }

    private String sendTransferRequest(TransferDTO transferDTO) {
        try {
            paymentService.requestTransfer(transferDTO);
            return "request accepted; please wait until the request is approved by "
                + transferDTO.getPayerEmail();
        } catch (TransferServiceExternalException e) {
            return e.getMessage();
        }
    }

    private void preTransferRequestAction(Chat chat, Bot bot) {
        Message ackMsg = new Message("requesting transfer...", chat, bot, LocalDateTime.now());
        messageService.saveMessage(ackMsg);
    }
}
