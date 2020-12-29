package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repogitory.MemberRepogitory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepogitory memberRepogitory;
//
//    public MemberService(MemberRepogitory memberRepogitory) {
//        this.memberRepogitory = memberRepogitory;
//    }

    //회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepogitory.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepogitory.findByName(member.getName());
        if(!findMember.isEmpty()){
           throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //회원 전체조회
    public List<Member> findMembers(){
        return memberRepogitory.findAll();
    }
     public Member findOne(Long memberId){
        return memberRepogitory.findOne(memberId);
     }
}
