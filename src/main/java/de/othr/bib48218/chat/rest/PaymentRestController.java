package de.othr.bib48218.chat.rest;

import com.othr.swvigopay.entity.TransferDTO;
import de.othr.bib48218.chat.util.RestAccessException;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.PeerChat;
import de.othr.bib48218.chat.entity.ServiceType;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFMessageService;
import de.othr.bib48218.chat.service.IFUserService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/callback/vigopay/transfers/stateUpdate")
public class PaymentRestController {

    private static final ServiceType serviceType = ServiceType.PAYMENT;

    @Value("${partner.service.payment.access-token}")
    private String requestToken;

    @Value("${partner.service.payment.bot_name}")
    private String botName;

    @Autowired
    private IFUserService userService;

    @Autowired
    private IFMessageService messageService;

    @Autowired
    private IFChatService chatService;

    @PostMapping()
    public void nameDerMethode(
        @RequestBody TransferDTO transferDTO,
        @RequestHeader("access-token") String token
    ) {
        if (requestToken.equals(token)) {
            // Send message about status update to all person whose email matches receiver's one.
            userService.getBotByUsername(botName).ifPresent(b ->
                userService.getPersonByEmail(transferDTO.getReceiverEmail()).forEach(p -> {
                    PeerChat chat = chatService.getOrCreatePeerChatOf(p, b);
                    messageService.saveMessage(
                        new Message(
                            transferDtoToMessageText(transferDTO), chat, p, LocalDateTime.now())
                    );
                }));
        } else {
            throw new RestAccessException(serviceType);
        }
    }

    private String transferDtoToMessageText(TransferDTO transferDTO) {
        return "Status update regarding transfer request to " + transferDTO.getPayerEmail()
            + " with description '" + transferDTO.getDescription() + "'"
            + " about " + transferDTO.getAmount() + ":\n"
            + transferDTO.getState();
    }
}
