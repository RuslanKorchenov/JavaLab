package json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import json.authentication.LoginPayload;
import json.authentication.LoginStatusPayload;
import json.authentication.LogoutPayload;
import json.command.*;
import json.help.HelpPayload;
import json.messages.MessagePayload;
import json.messages.MessagesPayload;
import json.messages.SingleMessagePayload;
import json.products.ProductsPayload;
import json.status.FailPayload;
import json.status.SuccessPayload;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CommandPayload.class, name = "command"),
        @JsonSubTypes.Type(value = LoginPayload.class, name = "login"),
        @JsonSubTypes.Type(value = LogoutPayload.class, name = "logout"),
        @JsonSubTypes.Type(value = MessagePayload.class, name = "message"),
        @JsonSubTypes.Type(value = MessagesPayload.class, name = "messages"),
        @JsonSubTypes.Type(value = SingleMessagePayload.class, name = "aloneMessage"),
        @JsonSubTypes.Type(value = LoginStatusPayload.class, name = "loginStatus"),
        @JsonSubTypes.Type(value = HelpPayload.class, name = "helpResponse"),
        @JsonSubTypes.Type(value = CommandListPayload.class, name = "list"),
        @JsonSubTypes.Type(value = CommandProductListPayload.class, name = "productList"),
        @JsonSubTypes.Type(value = CommandBuyProductPayload.class, name = "buyProduct"),
        @JsonSubTypes.Type(value = CommandRemoveProductPayload.class, name = "removeProduct"),
        @JsonSubTypes.Type(value = CommandAddProductPayload.class, name = "addProduct"),
        @JsonSubTypes.Type(value = ProductsPayload.class, name = "getProducts"),
        @JsonSubTypes.Type(value = SuccessPayload.class, name = "success"),
        @JsonSubTypes.Type(value = FailPayload.class, name = "fail"),
})

public abstract class AbstractPayload {
}
