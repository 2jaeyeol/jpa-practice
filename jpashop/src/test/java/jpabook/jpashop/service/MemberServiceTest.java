package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repogitory.MemberRepogitory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberRepogitory memberRepogitory;
    @Autowired MemberService memberService;
    @Autowired EntityManager em;

    @Test
//    @Rollback(value = false)
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("이재열");
        //when

        Long saveId = memberService.join(member);
        //then
        em.flush();
        assertEquals(member,memberRepogitory.findOne(saveId));
    }
    @Test
    public void 중복회원() throws Exception{
       //given
        Member member1 = new Member();
        member1.setName("이이");
        Member member2 = new Member();
        member1.setName("이이");
        //when
        memberService.join(member1);
        memberService.join(member2);
        //then
        //fail();



    }

}