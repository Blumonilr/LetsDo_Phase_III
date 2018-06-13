package YingYingMonster.LetsDo_Phase_III.repository.event;

import YingYingMonster.LetsDo_Phase_III.entity.event.PublishEvent;
import org.hibernate.boot.model.source.spi.JpaCallbackSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PublishEventRepository extends JpaRepository<PublishEvent, Long> {

    public List<PublishEvent> findByPublisherId(long publisherId);

    public List<PublishEvent> findByDateBetween(Date start, Date end);

    public List<PublishEvent> findByDateBefore(Date date);

    public List<PublishEvent> findByDateAfter(Date date);
}
