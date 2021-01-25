package de.othr.bib48218.chat;

import com.othr.swvigopay.entity.TransferDTO;
import com.othr.swvigopay.exceptions.TransferServiceExternalException;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.ServiceType;
import de.othr.bib48218.chat.service.IFPaymentService;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventSubscriber implements ApplicationListener<PartnerServiceEvent> {

    private static final Pattern transferMessage = Pattern.compile(
        "^/transfer (?<amount>[0-9]+(\\.[0-9]*)?) (?<email>[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6})\\s*(?<description>.*)$",
        Pattern.CASE_INSENSITIVE);

    @Autowired
    private IFPaymentService paymentService;

    @Override
    public void onApplicationEvent(PartnerServiceEvent event) {
        if (event.getServiceType() == ServiceType.PAYMENT) {
            Message message = event.getSource();
            Matcher matcher = transferMessage.matcher(message.getText());
            if (matcher.matches()) {
                String payerEmail = ((Person) message.getAuthor()).getEmail();
                String receiverEmail = matcher.group("email");
                BigDecimal amount = BigDecimal.valueOf(Long.parseLong(matcher.group("amount")));
                String description = matcher.group("description");

                TransferDTO dto = new TransferDTO(payerEmail, receiverEmail, amount, description);

                try {
                    paymentService.transfer(dto);
                } catch (TransferServiceExternalException e) {
                }
            }
        }
    }
}
