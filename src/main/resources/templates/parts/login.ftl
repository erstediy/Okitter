<#macro login path replace isRegisterForm>
    <form action="${path}" method="post">
        <div class="mb-3">
            <label class="col-sm-2 col-form-label">Username: </label>
            <div class="col-sm-6">
                <input type="text" class="form-control ${(usernameError??)?string('is-invalid','')}" name="username"
                       placeholder="Username"
                       value="<#if user??>${user.username}</#if>"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="mb-3">
            <label class="col-sm-2 col-form-label">Password: </label>
            <div class="col-sm-6">
                <input type="password" class="form-control ${(passwordError??)?string('is-invalid','')}" name="password"
                       placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="mb-3">
                <label class="col-sm-2 col-form-label">Password: </label>
                <div class="col-sm-6">
                    <input type="password" class="form-control"
                           name="password2" placeholder="Retype password"/>
                </div>
            </div>
            <div class="mb-3">
                <label class="col-sm-2 col-form-label">Email: </label>
                <div class="col-sm-6">
                    <input type="email" class="form-control ${(emailError??)?string('is-invalid','')}" name="email"
                           placeholder="yourmail@mail.com"
                           value="<#if user??>${user.email}</#if>"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm>
            <button type="button" class="btn btn-link"
                    onclick="window.location.replace('http://localhost:8080/registration')">Зарегистрироваться
            </button>
        </#if>
        <button type="submit" class="btn btn-primary"><#if isRegisterForm>Создать<#else>Войти</#if></button>
        <button type="reset" class="btn btn-secondary">Сбросить</button>
        <button type="button" class="btn btn-secondary" onclick="window.location.replace('${replace}')">Отменить
        </button>
        <#-- <#nested>-->
    </form>

</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Выйти</button>
    </form>
</#macro>