package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.TextNode;
import YingYingMonster.LetsDo_Phase_III.repository.TextNodeRepository;
import YingYingMonster.LetsDo_Phase_III.service.TextNodeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TextNodeServiceImpl implements TextNodeService {

    @Autowired
    TextNodeRepository tnr;
    @Override
    public List<TextNode> findByFather(String father) {
        return tnr.findByFather(father);
    }

    @Override
    public TextNode findByName(String name) {
        return tnr.findByName(name);
    }

    @Override
    public List<TextNode> findAllFather() {
        return tnr.findByFather(null);
    }

    @Override
    public boolean addTextNode(TextNode textNode) {
        if(tnr.save(textNode)!=null)
            return true;
        else
            return false;
    }
}
