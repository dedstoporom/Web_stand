<#include "parts/security.ftlh">
<#import "parts/common.ftlh" as c>
<@c.page>
    <div><a href="/main">Back</a></div>
    <div class="mt-2"><h1>${userChannel.username}</h1></div>
    <#if !isCurrentUser>
        <#if isSubscriber>
            <a class="btn btn-primary" href="/user/profile/unsubscribe/${userChannel.id}">In subscriptions</a>
        <#else>
            <a class="btn btn-secondary" href="/user/profile/subscribe/${userChannel.id}">Not in subscriptions</a>
        </#if>
    </#if>
        <div class="row">
            <div class="col-sm-2">
                <div class="card mt-2" style="width: 10rem;">
                    <div class="card-body">
                        <h6 class="card-text">Subscription</h6>
                        <a href="/user/profile/subscriptions/list/${userChannel.id}"><span>${Subscriptions}</span></a>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="card mt-2" style="width: 10rem;">
                    <div class="card-body">
                        <h6 class="card-text">Subscribers</h6>
                            <a href="/user/profile/subscribers/list/${userChannel.id}"><span>${Subscribers}</span></a>
                    </div>
                </div>
            </div>
        </div>
        <#list messages as messages>
            <div class="card mt-2 " style="width: 20rem;">
                <div class="card-body">
                    <span>${messages.text}</span>
                    <#if messages.tag??>
                        <span>#${messages.tag}</span>
                    </#if>
                </div>
                <#if messages.filename??>
                    <img class="card-img-bot" src="/img/${messages.filename}"/> <#--/img/для подгрузки КАРТИНОК по ссылке-->
                </#if>
                <div class="card-footer text-muted">
                    ${messages.date}
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</@c.page>