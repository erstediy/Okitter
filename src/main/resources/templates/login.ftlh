<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as loginPage>
<@c.page "Log in">
    ${message!}
<@loginPage.login "/login" "http://localhost:8080/" false/>
</@c.page>