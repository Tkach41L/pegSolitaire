package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentException;
import sk.tuke.gamestudio.service.CommentService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) throws CommentException {
        entityManager.persist(comment);
    }

    @Override
    public List getComments(String game) throws CommentException {
        return entityManager.createNamedQuery("Comment.getComments").setParameter("game", game)
                .setMaxResults(10).getResultList();
    }

    @Override
    public void reset() throws CommentException {
        entityManager.createNamedQuery("Comment.resetComments").executeUpdate();
    }
}
