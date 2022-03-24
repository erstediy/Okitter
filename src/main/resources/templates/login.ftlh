<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as loginPage>
<@c.page "Log in">
    <#if Session?? && RequestParameters.error??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if RequestParameters.logout??>
        <div class="alert alert-info" role="alert">
            Выход из аккаунта прошёл успешно!
        </div>
    </#if>
    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
<@loginPage.login "/login" "http://localhost:8080/" false/>
</@c.page>