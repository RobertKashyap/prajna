package com.prajna.mentor_extension.Exchanges;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckStyle {
    List<Boolean> checkboxes;// boolean checkbox to check the ordered list, find the list below*
    List<List<Integer>> occuranceLineNumbers;// corresponding line numbers for occurances and reason for false
                                             // occurance at the given line number i.e user didnt follow the rule
    Integer Score; // out of ten
}