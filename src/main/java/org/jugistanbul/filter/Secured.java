package org.jugistanbul.filter;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 23.10.2021
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured { }
