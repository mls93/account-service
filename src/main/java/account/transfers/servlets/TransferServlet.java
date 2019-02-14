package account.transfers.servlets;

import account.transfers.service.AccountService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

import static account.transfers.servlets.ParseUtils.parseAccountId;
import static account.transfers.servlets.ParseUtils.parseMoney;

@RequiredArgsConstructor( onConstructor = @__({ @Inject}))
public class TransferServlet extends HttpServlet {
    private final AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Long srcAccountId = parseAccountId(req, "src");
        Long destAccountId = parseAccountId(req, "dest");
        BigDecimal money = parseMoney(req, "value");
        accountService.transfer(srcAccountId, destAccountId, money);
    }
}
