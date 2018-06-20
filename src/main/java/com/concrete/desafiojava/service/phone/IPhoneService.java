package com.concrete.desafiojava.service.phone;

import java.util.List;

import com.concrete.desafiojava.domain.orm.Phone;
import com.concrete.desafiojava.domain.orm.ApplicationUser;

public interface IPhoneService {

	public List<Phone> findUserPhones(ApplicationUser user);

	public List<Phone> savePhones(List<Phone> phoneListToSave);

}
