<#import "parts/common.ftl" as c>
<@c.page "Profile">
    <h5>${username}</h5>
    ${message!}
    <form method="post">
        <div class="mb-3">
            <label class="col-sm-2 col-form-label">Password: </label>
            <div class="col-sm-6">
                <input type="password" class="form-control" name="password" placeholder="Password"/>
            </div>
        </div>
        <div class="mb-3">
            <label class="col-sm-2 col-form-label">Email: </label>
            <div class="col-sm-6">
                <input type="email" class="form-control" name="email" placeholder="yourmail@mail.com" value="${email!''}"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <button type="submit" class="btn btn-primary">Сохранить</button>
        <button type="reset" class="btn btn-secondary">Сбросить</button>
        <#-- <#nested>-->
    </form>
</@c.page>