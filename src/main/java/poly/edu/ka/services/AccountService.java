package poly.edu.ka.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import poly.edu.ka.domain.Account;

public interface AccountService {

	void deleteAll();

	<S extends Account> List<S> findAll(Example<S> example, Sort sort);

	<S extends Account> List<S> findAll(Example<S> example);

	void deleteAll(Iterable<? extends Account> entities);

	Account getReferenceById(String id);

	void deleteAllById(Iterable<? extends String> ids);

	void delete(Account entity);

	Account getById(String id);

	void deleteById(String id);

	long count();

	<S extends Account, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	Account getOne(String id);

	void deleteAllInBatch();

	<S extends Account> boolean exists(Example<S> example);

	void deleteAllByIdInBatch(Iterable<String> ids);

	<S extends Account> long count(Example<S> example);

	boolean existsById(String id);

	void deleteAllInBatch(Iterable<Account> entities);

	Optional<Account> findById(String id);

	<S extends Account> Page<S> findAll(Example<S> example, Pageable pageable);

	void deleteInBatch(Iterable<Account> entities);

	List<Account> findAllById(Iterable<String> ids);

	List<Account> findAll();

	<S extends Account> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Account> S saveAndFlush(S entity);

	Page<Account> findAll(Pageable pageable);

	void flush();

	List<Account> findAll(Sort sort);

	<S extends Account> Optional<S> findOne(Example<S> example);

	<S extends Account> List<S> saveAll(Iterable<S> entities);

	<S extends Account> S save(S entity);

	Account login(String username, String password);

}
