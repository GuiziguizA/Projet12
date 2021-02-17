package sid.org.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sid.org.dto.DateDto;
import sid.org.exception.ForbiddenException;

@Service
public class DateServiceImpl implements DateService {

	private static final Logger logger = LoggerFactory.getLogger(DateServiceImpl.class);

	@Override
	public Date createDate(DateDto dateDto) throws ForbiddenException {

		String sDate6 = dateDto.getAnnee() + "-" + dateDto.getMois() + "-" + dateDto.getJour() + " "
				+ dateDto.getHeure();
		logger.info(sDate6);
		SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Date date = formatter6.parse(sDate6);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new ForbiddenException("la date est incorrect");
		}

	}

}
