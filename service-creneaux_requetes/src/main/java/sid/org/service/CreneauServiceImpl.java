package sid.org.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sid.org.classe.Creneau;
import sid.org.dao.CreneauRepository;
import sid.org.dto.CreneauDto;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ResultNotFoundException;

@Service
public class CreneauServiceImpl implements CreneauService {
	@Autowired
	CreneauRepository creneauRepository;

	@Override
	public Creneau createCreneau(CreneauDto creneauDto) throws EntityAlreadyExistException {

		Optional<Creneau> creneau = creneauRepository.findByIdUserAndIdComp(creneauDto.getIdUser(),
				creneauDto.getIdComp());
		if (creneau.isPresent()) {
			throw new EntityAlreadyExistException("le creneau existe deja");
		}

		Creneau creneau1 = new Creneau();
		creneau1.setIdComp(creneauDto.getIdComp());
		creneau1.setDate(new Date());
		creneau1.setIdUser(creneauDto.getIdUser());
		creneau1.setIdUser1(creneauDto.getIdUser1());
		creneauRepository.saveAndFlush(creneau1);
		return creneau1;

	}

	@Override
	public Creneau getCreneau(Long id) throws ResultNotFoundException {
		Optional<Creneau> creneau = creneauRepository.findById(id);
		if (!creneau.isPresent()) {
			throw new ResultNotFoundException("creneau introuvable");
		}
		return creneau.get();

	}

	@Override
	public Page<Creneau> getCreneaux(Long idUser) {

		Pageable pageable = PageRequest.of(0, 2);
		Page<Creneau> creneaux = creneauRepository.findByIdUser(idUser, pageable);

		return creneaux;

	}

	@Override
	public void deleteCreneau(Long id) throws ResultNotFoundException {
		Optional<Creneau> creneau = creneauRepository.findById(id);
		if (!creneau.isPresent()) {
			throw new ResultNotFoundException("creneau introuvable");
		}
		creneauRepository.delete(creneau.get());

	}

}
