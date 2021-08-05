<#import "parts/common.ftlh" as c>
<@c.page>
   <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
      <div class="alert alert-danger" role="alert">
         ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
      </div>
   </#if>
   <@c.user_page "/login" false>
   </@c.user_page>
</@c.page>