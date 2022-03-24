<#include "security.ftl">
<#import "login.ftl" as loginPage>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Okkiter</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/">Greeting</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/home">Home</a>
                </li>
                <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/user">User List</a>
                </li>
                </#if>
                <#if known>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/user/profile">Profile</a>
                    </li>
                </#if>
            </ul>
        </div>
        <div class="navbar-text me-3">${name}</div>
        <#if name !="unknown"><@loginPage.logout/></#if>
    </div>
</nav>