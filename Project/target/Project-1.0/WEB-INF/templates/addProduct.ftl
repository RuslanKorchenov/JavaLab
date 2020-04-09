<#if check??>
    ${check}
</#if>
<form id="productForm" action="/products/add" method="post">
    <input id="name" type="text" name="name" placeholder="Product name">
    <input id="price" type="number" name="price" placeholder="Price">
    <input type="submit" name="addProduct" id="addProduct" class="form-submit" value="Add product"/>
</form>
