package org.jugistanbul.security;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 23.02.2022
 ***/
public record Resource(String path, String[] roles, boolean permitAll) { }
