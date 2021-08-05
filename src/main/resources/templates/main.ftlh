<#include "parts/security.ftlh">
<#import "parts/common.ftlh" as c>
<@c.page>
<#--post для загрузки на сайт(в бд)-->
    <form action="/main" method="post" enctype="multipart/form-data">
        <#if textError??>
            <div>${textError}</div>
        </#if>
        <input type="text" name="text" placeholder="Message"/>
        <input type="text" name="tag" placeholder="Tag"/>
        <input type="file" name="file"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" name="send" >Send</button>
    </form>
<#--get для отображения того ,что уже на сайте-->
    <div class="mt-4 mb-4">
    <form action="/main" method="get">
        <input type="text" name="tag_field" value="${RequestParameters.tag_field!}"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" name="send">Search</button>
    </form>
    </div>
    <div id="message-list">
    <#list messages as messages>
        <div class="card mt-2 " style="width: 20rem;" data-id="${messages.id}">
        <div class="card-body">
            <a href="user/profile/${messages.author.id}"><strong>${messages.getAuthorName()}</strong></a>
            <span>${messages.text}</span>
            <#if messages.tag??>
            <span>#${messages.tag}</span>
            </#if>
            <#if currentUserId==messages.author.id>
                <a href="/message/${messages.id}">Edit</a>
            </#if>
        </div>
            <#if messages.filename??>
                <img class="card-img-bot" src="/img/${messages.filename}"/> <#--/img/для подгрузки КАРТИНОК по ссылке-->
            </#if>
            <div class="card-footer text-muted">
            ${messages.date}
                <a href="/message/${messages.id}/like">
                    <#if messages.meLiked>
                        <i class="fas fa-heart"></i>
                    <#else>
                        <i class="far fa-heart"></i>
                    </#if>
                </a>
                ${messages.likes}
            </div>
        </div>
    <#else>
        No message
    </#list>
    </div>
</@c.page>