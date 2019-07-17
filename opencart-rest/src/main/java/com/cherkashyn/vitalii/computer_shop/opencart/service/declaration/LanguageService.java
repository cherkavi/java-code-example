package com.cherkashyn.vitalii.computer_shop.opencart.service.declaration;

import com.cherkashyn.vitalii.computer_shop.opencart.domain.Language;
import com.cherkashyn.vitalii.computer_shop.opencart.service.exception.ServiceException;

public interface LanguageService {
	Language find(int id) throws ServiceException;
	Language find(String name) throws ServiceException;
	Language findDefaultLanguage() throws ServiceException;
}
