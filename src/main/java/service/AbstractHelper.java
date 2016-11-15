package service;

import com.jaunt.Element;
import com.jaunt.NotFound;
import com.jaunt.UserAgent;

public abstract class AbstractHelper {

    protected Element getFirst(String findData, UserAgent linkAgent) throws NotFound {
        return linkAgent.doc.findFirst(findData);
    }

    protected String getTitle(Element element){
        return element.getText().trim();
    }
}
