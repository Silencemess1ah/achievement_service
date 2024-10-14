package faang.school.achievement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEvent {
    private Long idAuthor;
    private Long idPost;
    private Long idComment;
    private String comment;
}
