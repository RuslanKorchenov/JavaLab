<#import "spring.ftl" as spring />
<#if check??>
    ${check}
</#if>
<form id="productForm" action="/products/add" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input id="name" type="text" name="name" placeholder="<@spring.message 'product.name'/>">
    <input id="price" type="number" name="price" placeholder="<@spring.message 'product.price'/>">
    <input type="submit" name="addProduct" id="addProduct" class="form-submit" value="<@spring.message 'product.add'/>"/>
</form>
