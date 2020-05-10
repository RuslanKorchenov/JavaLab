<#import "spring.ftl" as spring />
<#if check??>
    ${check}
</#if>
<#list products as product>
    <tr>
        <td>${product.id}.</td>
        <td><@spring.message 'product.name'/>: ${product.name}.</td>
        <td><@spring.message 'product.price'/>: ${product.price} RUB. <br></td>
    </tr>
</#list>