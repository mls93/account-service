package ru.mls.account.servlets;

import lombok.RequiredArgsConstructor;
import ru.mls.AccountService;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

import static ru.mls.account.servlets.ParseUtils.parseAccountId;
import static ru.mls.account.servlets.ParseUtils.parseMoney;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class AccountCreateServlet extends HttpServlet {
    private final AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Long id = parseAccountId(req, "id");
        BigDecimal money = parseMoney(req, "balance");
        accountService.createAccount(id, money);
    }
}
