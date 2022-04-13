<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as loginPage>
<@c.page "Sign in">
    ${message!}
<div class="mb-1">Меня зовут Славик, я просто здесь сижу и наблюдаю :)</div>
    <@loginPage.login "/registration" "http://localhost:8080/login" true/>
</@c.page>