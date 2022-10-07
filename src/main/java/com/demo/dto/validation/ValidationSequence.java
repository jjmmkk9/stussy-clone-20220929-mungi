package com.demo.dto.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({//우선순위 저장
        ValidationGroups.NotBlankGroup.class,
        ValidationGroups.PatternCheckGroup.class,
        ValidationGroups.SizeCheckGroup.class,
        Default.class //이거 뭔데
})
public interface ValidationSequence {

}
