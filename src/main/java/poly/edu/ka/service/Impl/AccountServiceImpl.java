package poly.edu.ka.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import poly.edu.ka.domain.Account;
import poly.edu.ka.repository.AccountRepository;
import poly.edu.ka.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Account login(String username, String password)
	{
		Optional<Account> optExist = findById(username);
		if(optExist.isPresent() && bCryptPasswordEncoder.matches(password, optExist.get().getPassword()))
		{
			optExist.get().setPassword("");
			return optExist.get();
		}
		return null;
	}
	
	@Override
	public <S extends Account> S save(S entity) {
		Optional<Account> optExist = findById(entity.getUsername());
		
		if(optExist.isPresent())
		{
			if(StringUtils.isEmpty(entity.getPassword()))
			{
				entity.setPassword(optExist.get().getPassword());
			}
			else
			{
				//nếu cập nhật thì mã hóa password
				entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
			}
		}else
		{
			//nếu tạo mới thì mã hóa password
			entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
		}

		return accountRepository.save(entity);
	}
	
	@Override
	public <S extends Account> List<S> saveAll(Iterable<S> entities) {
		return accountRepository.saveAll(entities);
	}

	@Override
	public <S extends Account> Optional<S> findOne(Example<S> example) {
		return accountRepository.findOne(example);
	}

	@Override
	public List<Account> findAll(Sort sort) {
		return accountRepository.findAll(sort);
	}

	@Override
	public void flush() {
		accountRepository.flush();
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		return accountRepository.findAll(pageable);
	}

	@Override
	public <S extends Account> S saveAndFlush(S entity) {
		return accountRepository.saveAndFlush(entity);
	}

	@Override
	public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
		return accountRepository.saveAllAndFlush(entities);
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public List<Account> findAllById(Iterable<String> ids) {
		return accountRepository.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<Account> entities) {
		accountRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
		return accountRepository.findAll(example, pageable);
	}

	@Override
	public Optional<Account> findById(String id) {
		return accountRepository.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<Account> entities) {
		accountRepository.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(String id) {
		return accountRepository.existsById(id);
	}

	@Override
	public <S extends Account> long count(Example<S> example) {
		return accountRepository.count(example);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<String> ids) {
		accountRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends Account> boolean exists(Example<S> example) {
		return accountRepository.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		accountRepository.deleteAllInBatch();
	}

	@Override
	public Account getOne(String id) {
		return accountRepository.getOne(id);
	}

	@Override
	public <S extends Account, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return accountRepository.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return accountRepository.count();
	}

	@Override
	public void deleteById(String id) {
		accountRepository.deleteById(id);
	}

	@Override
	public Account getById(String id) {
		return accountRepository.getById(id);
	}

	@Override
	public void delete(Account entity) {
		accountRepository.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		accountRepository.deleteAllById(ids);
	}

	@Override
	public Account getReferenceById(String id) {
		return accountRepository.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Account> entities) {
		accountRepository.deleteAll(entities);
	}

	@Override
	public <S extends Account> List<S> findAll(Example<S> example) {
		return accountRepository.findAll(example);
	}

	@Override
	public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
		return accountRepository.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		accountRepository.deleteAll();
	}
	
	
}
