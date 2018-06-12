package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.TextNode;

import java.util.List;

public interface TextNodeService {
    public List<TextNode> findByFather(String father);

    public TextNode findByName(String name);

    public List<TextNode> findAllFather();

    public boolean addTextNode(TextNode textNode);
}
