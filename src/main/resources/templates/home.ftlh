<#import "parts/common.ftl" as c>
<@c.page "Home">
    <form method="get" action="/home" class="row g-1 mb-2">
        <div class="col-md-6">
            <input type="text" name="tag" class="form-control" value="${tag!}" placeholder="Поиск по тэгу">
        </div>
        <div class="col">
            <button type="submit" class="btn btn-primary">Поиск</button>
        </div>
    </form>


    <a class="btn btn-primary" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Add new message
    </a>
    <div class="collapse <#if message??>show</#if>" id="collapseExample">
        <form method="post" action="/home" enctype="multipart/form-data">
            <div class="row g-2 my-3">
                <div class="col-md-6">
                    <input type="text" value="<#if message??>${message.text}</#if>"
                           class="form-control ${(textError??)?string('is-invalid','')}"
                           name="text" placeholder="Введите сообщение">
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>

                <div class="col-md-6">
                    <input type="text" class="form-control ${(tagError??)?string('is-invalid','')}"
                           name="tag" placeholder="Введите тэг"
                           value="<#if message??>${message.tag}</#if>">
                    <#if tagError??>
                        <div class="invalid-feedback">
                            ${tagError}
                        </div>
                    </#if>
                </div>
            </div>

            <div class="mb-3">
                <input class="form-control" name="file" type="file">
            </div>
            <div class="invalid-feedback">
                Please choose a username.
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary mb-3">Добавить</button>
        </form>
    </div>

    <div class="container">
        <div class="row" data-masonry='{"percentPosition": true }'>
            <#list messages as message>
                <div class="col-lg-4 col-md-6 col-6">
                    <div class="card my-3">
                        <#if message.filename??>
                            <img src="/img/${message.filename}" class="card-image-top card-v" alt="">
                        </#if>
                        <div class="card-body">
                            <#--<div class="m-2">
                                <span>${message.text}</span>
                                <i>${message.tag}</i>
                            </div>-->
                            <h5 class="card-title">${message.tag}</h5>
                            <p class="card-text">${message.text}</p>
                            <div class="card-footer text-muted">
                                ${message.authorName}</div>
                        </div>
                    </div>
                </div>
            <#else>
                No messages
            </#list>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/masonry-layout@4.2.2/dist/masonry.pkgd.min.js"
            integrity="sha384-GNFwBvfVxBkLMJpYMOABq3c+d3KnQxudP/mGPkzpZSTYykLBNsZEnG2D9G/X/+7D" crossorigin="anonymous"
            async></script>
</@c.page>