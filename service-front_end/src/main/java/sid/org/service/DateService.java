package sid.org.service;

import java.util.Date;

import sid.org.dto.DateDto;
import sid.org.exception.ForbiddenException;

public interface DateService {

	Date createDate(DateDto dateDto) throws ForbiddenException;

}
