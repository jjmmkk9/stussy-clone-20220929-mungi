
package com.demo.dto.validation;

/*
인터페이스 안에 인터페이스 => 이너 클래스
validation 의 그룹을 설정해놓은 인터페이스
 */
public interface ValidationGroups {
    public interface NotBlankGroup{};
    public interface PatternCheckGroup{};
    public interface SizeCheckGroup{};
}
