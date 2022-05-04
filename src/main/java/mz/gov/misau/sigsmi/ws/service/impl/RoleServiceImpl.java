package mz.gov.misau.sigsmi.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mz.gov.misau.sigsmi.ws.io.model.entity.RoleEntity;
import mz.gov.misau.sigsmi.ws.io.repository.RoleRepository;
import mz.gov.misau.sigsmi.ws.service.RoleService;
import mz.gov.misau.sigsmi.ws.shared.MyUtils;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository repository;

	@Autowired
	private MyUtils utility;

	@Override
	public void create() {

		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRoleName("ROLE_EXPORT_USER_PDF");
		roleEntity.setRoleId(utility.generateUrlResource(30));
		roleEntity.setRoleDescription("Permissao para exportart pdf de utilizadores");
		repository.save(roleEntity);

		RoleEntity roleEntity2 = new RoleEntity();
		roleEntity2.setRoleName("ROLE_EXPORT_USER_EXCEL");
		roleEntity2.setRoleId(utility.generateUrlResource(30));
		roleEntity.setRoleDescription("Permissao para exportart excel de utilizadores");
		repository.save(roleEntity2);

		RoleEntity roleEntity3 = new RoleEntity();
		roleEntity3.setRoleName("ROLE_EXPORT_USER_CSV");
		roleEntity3.setRoleId(utility.generateUrlResource(30));
		roleEntity.setRoleDescription("Permissao para exportart csv de utilizadores");
		repository.save(roleEntity3);

		/*
		 * RoleEntity roleEntity4 = new RoleEntity(); roleEntity4.setRoleName("ROLE_");
		 * roleEntity4.setRoleId(utility.generateUrlResource(30));
		 * roleEntity4.setRoleDescription("Permissao para visualizar as recem nascidos "
		 * ); repository.save(roleEntity4);
		 *
		 * RoleEntity roleEntity5 = new RoleEntity();
		 * roleEntity5.setRoleName("ROLE_CREATE_KIDS");
		 * roleEntity5.setRoleId(utility.generateUrlResource(30));
		 * roleEntity5.setRoleDescription("Permissao para adicionar as recem nascidos "
		 * ); repository.save(roleEntity5);
		 *
		 * RoleEntity roleEntity6 = new RoleEntity();
		 * roleEntity6.setRoleName("ROLE_UPDATE_KIDS");
		 * roleEntity6.setRoleId(utility.generateUrlResource(30));
		 * roleEntity6.setRoleDescription("Permissao para actualizar as recem nascidos "
		 * ); repository.save(roleEntity4);
		 *
		 * RoleEntity roleEntity7 = new RoleEntity();
		 * roleEntity7.setRoleName("ROLE_DELETE_KIDS");
		 * roleEntity7.setRoleId(utility.generateUrlResource(30));
		 * roleEntity7.setRoleDescription("Permissao para remover as recem nascidos ");
		 * repository.save(roleEntity7);
		 *
		 * RoleEntity roleEntity8 = new RoleEntity();
		 * roleEntity8.setRoleName("ROLE_SELECT_PREGNANCY");
		 * roleEntity8.setRoleId(utility.generateUrlResource(30));
		 * roleEntity8.setRoleDescription("Permissao para visualizar gravidez");
		 * repository.save(roleEntity8);
		 */

		/*
		 * RoleEntity roleEntity9 = new RoleEntity();
		 * roleEntity9.setRoleName("ROLE_CREATE_PREGNANCY");
		 * roleEntity9.setRoleId(utility.generateUrlResource(30));
		 * roleEntity9.setRoleDescription("Permissao para adicionar gravidez");
		 * repository.save(roleEntity9);
		 *
		 * RoleEntity roleEntity10 = new RoleEntity();
		 * roleEntity10.setRoleName("ROLE_UPDATE_PREGNANCY");
		 * roleEntity10.setRoleId(utility.generateUrlResource(30));
		 * roleEntity10.setRoleDescription("Permissao para atualizar gravidez");
		 * repository.save(roleEntity10);
		 *
		 * RoleEntity roleEntity11 = new RoleEntity();
		 * roleEntity11.setRoleName("ROLE_DELETE_PREGNANCY");
		 * roleEntity11.setRoleId(utility.generateUrlResource(30));
		 * roleEntity11.setRoleDescription("Permissao para remover gravidez");
		 * repository.save(roleEntity11);
		 */

	}

}
