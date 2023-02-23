package com.example.todoapp.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.regex.Pattern;

public class ICheckBCryptPasswordEncoder implements PasswordEncoder {
    private Pattern BCRYPT_PATTERN;
    private final Log logger;
    private final int strength;
    private final SecureRandom random;

    public ICheckBCryptPasswordEncoder() {
        this(-1);
    }

    public ICheckBCryptPasswordEncoder(int strength) {
        this(strength, (SecureRandom)null);
    }

    public ICheckBCryptPasswordEncoder(int strength, SecureRandom random) {
        this.BCRYPT_PATTERN = Pattern.compile("^\\$2[aby]?\\$\\d{1,2}\\$[.\\/A-Za-z0-9]{53}$");
        this.logger = LogFactory.getLog(this.getClass());
        if (strength == -1 || strength >= 4 && strength <= 31) {
            this.strength = strength;
            this.random = random;
        } else {
            throw new IllegalArgumentException("Bad strength");
        }
    }

    public String encode(CharSequence rawPassword) {
        String salt;
        if (this.strength > 0) {
            if (this.random != null) {
                salt = IcheckBcrypt.gensalt(this.strength, this.random);
            } else {
                salt = IcheckBcrypt.gensalt(this.strength);
            }
        } else {
            salt = IcheckBcrypt.gensalt();
        }

        return IcheckBcrypt.hashpw(rawPassword.toString(), salt);
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            if (!this.BCRYPT_PATTERN.matcher(encodedPassword).matches()
            ) {
                this.logger.warn("Encoded password does not look like BCrypt");
                return false;
            } else {
                return IcheckBcrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        } else {
            this.logger.warn("Empty encoded password");
            return false;
        }
    }
}

