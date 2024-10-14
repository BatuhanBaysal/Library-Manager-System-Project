package com.batuhan.library.service.impl;

import com.batuhan.library.dto.AddressDTO;
import com.batuhan.library.dto.MemberDTO;
import com.batuhan.library.entity.Address;
import com.batuhan.library.entity.Member;
import com.batuhan.library.exception.ResourceNotFoundException;
import com.batuhan.library.mapper.AddressMapper;
import com.batuhan.library.mapper.MemberMapper;
import com.batuhan.library.repository.AddressRepository;
import com.batuhan.library.repository.MemberRepository;
import com.batuhan.library.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
    private MemberRepository memberRepository;
    private AddressRepository addressRepository;
    private AddressServiceImpl addressService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public MemberDTO addMember(MemberDTO memberDTO) {
        Address address = new Address();
        AddressDTO addressDTO = memberDTO.getAddress();

        if (addressDTO != null) {
            address = AddressMapper.mapToAddressEntity(addressDTO);
            address = addressRepository.save(address);
        }
        logger.info("Trying to add a member: {}", memberDTO);
        Member member = MemberMapper.mapToMemberEntity(memberDTO);
        if (address != null) {
            member.setAddress(address);
        }

        logger.info("Member entity after the mapping: {}", member);
        member = memberRepository.save(member);
        logger.info("The member successfully saved in database: {}", member);
        return MemberMapper.mapToMemberDTO(member);
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberMapper::mapToMemberDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO getMemberById(Long id) {
        // Optional<Member> optionalMember = memberRepository.findById(id);
        // Member member = optionalMember.get();
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member", "ID", id));
        return MemberMapper.mapToMemberDTO(member);
    }

    @Override
    @Transactional
    public MemberDTO updateMember(MemberDTO memberDTO) {
        Optional<Member> optionalMember = memberRepository.findById(memberDTO.getId());
        Member memberToUpdate = optionalMember.orElseThrow(
                () -> new ResourceNotFoundException("Member", "ID", memberDTO.getId())
        );
        updateMemberEntityFromDTO(memberToUpdate, memberDTO);
        memberToUpdate = memberRepository.save(memberToUpdate);
        return MemberMapper.mapToMemberDTO(memberToUpdate);
    }

    @Override
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)){
            throw new ResourceNotFoundException("Member", "ID", id);
        }
        memberRepository.deleteById(id);
    }

    @Override
    public List<MemberDTO> findMembersByCriteria(Long id, String firstName, String lastName, String barcodeNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> memberRoot = cq.from(Member.class);
        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(cb.equal(memberRoot.get("id"), id));
        }
        if (firstName != null) {
            predicates.add(cb.like(cb.lower(memberRoot.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }
        if (lastName != null) {
            predicates.add(cb.like(cb.lower(memberRoot.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }
        if (barcodeNumber != null) {
            predicates.add(cb.like(cb.lower(memberRoot.get("barcodeNumber")), "%" + barcodeNumber.toLowerCase() + "%"));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        List<Member> result = entityManager.createQuery(cq).getResultList();
        return result.stream()
                .map(MemberMapper::mapToMemberDTO)
                .collect(Collectors.toList());
    }

    private void updateMemberEntityFromDTO(Member memberToUpdate, MemberDTO memberDTO) {
        if (memberDTO.getFirstName() != null) {
            memberToUpdate.setFirstName(memberDTO.getFirstName());
        }
        if (memberDTO.getLastName() != null) {
            memberToUpdate.setLastName(memberDTO.getLastName());
        }
        if (memberDTO.getDateOfBirth() != null) {
            memberToUpdate.setDateOfBirth(LocalDate.parse(memberDTO.getDateOfBirth()));
        }
        if (memberDTO.getEmail() != null) {
            memberToUpdate.setEmail(memberDTO.getEmail());
        }
        if (memberDTO.getPhone() != null) {
            memberToUpdate.setPhone(memberDTO.getPhone());
        }
        if (memberDTO.getBarcodeNumber() != null) {
            memberToUpdate.setBarcodeNumber(memberDTO.getBarcodeNumber());
        }
        if (memberDTO.getMembershipStarted() != null) {
            memberToUpdate.setMembershipStarted(LocalDate.parse(memberDTO.getMembershipStarted()));
        }

        // member is active if membershipEnded = null
        if (memberDTO.getMembershipEnded() != null) {
            if (memberDTO.getMembershipEnded().isEmpty()) {
                memberToUpdate.setMembershipEnded(null);
                memberToUpdate.setIsActive(true);
            } else {
                memberToUpdate.setMembershipEnded(LocalDate.parse(memberDTO.getMembershipEnded()));
                memberToUpdate.setIsActive(false);
            }
        }

        // updating address
        if (memberDTO.getAddress() != null) {
            Address addressToUpdate;
            if (memberToUpdate.getAddress() != null) {
                addressToUpdate = memberToUpdate.getAddress();
            } else {
                addressToUpdate = new Address();
            }

            // update Address entity, use existing address service
            addressService.updateAddressEntityFromDTO(addressToUpdate, memberDTO.getAddress());
            addressRepository.save(addressToUpdate);
            memberToUpdate.setAddress(addressToUpdate);
        }
    }
}