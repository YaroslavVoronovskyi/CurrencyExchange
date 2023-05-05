package com.gmail.voronovskyi.yaroslav.service.impl;

import com.gmail.voronovskyi.yaroslav.model.Minfin;
import com.gmail.voronovskyi.yaroslav.repository.IMinfinRepository;
import com.gmail.voronovskyi.yaroslav.service.IMinfinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MinfinService implements IMinfinService {

    private final IMinfinRepository minfinRepository;

    @Autowired
    public MinfinService(IMinfinRepository minfinRepository) {
        this.minfinRepository = minfinRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Minfin> getAll() {
        List<Minfin> minfinsList = minfinRepository.findAll();
        if (minfinsList.isEmpty()) {
            throw new EntityNotFoundException("Minfin list is empty!");
        }
        return minfinsList;
    }

    @Override
    @Transactional
    public List<Minfin> save(List<Minfin> minfinsList) {
        return minfinRepository.saveAll(minfinsList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Minfin> search(String date) {
        List<Minfin> minfinsList = minfinRepository.search(date);
        if (minfinsList.isEmpty()) {
            throw new EntityNotFoundException("Minfin list is empty!");
        }
        return minfinsList;
    }
}
