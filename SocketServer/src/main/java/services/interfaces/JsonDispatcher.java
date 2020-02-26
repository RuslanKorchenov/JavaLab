package services.interfaces;

import dto.JsonDto;
import json.JsonTemplate;

public interface JsonDispatcher {
    JsonDto dispatch(JsonTemplate jsonTemplate);
}
