package com.gumo.demo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gumo.demo.cache.BusTypeCache;
import com.gumo.demo.entity.*;
import com.gumo.demo.enums.ColorCrowedEnum;
import com.gumo.demo.mapper.BaseTypeMapper;
import com.gumo.demo.mapper.MenuExtMapper;
import com.gumo.demo.mapper.MenuMapper;
import com.gumo.demo.mapper.UserMapper;
import com.gumo.demo.model.vo.MenuExtVO;
import com.gumo.demo.utils.ExcelUtil;
import com.gumo.demo.utils.PinYinUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

    @Autowired
    private BusTypeCache busTypeCache;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BaseTypeMapper baseTypeMapper;

    @Autowired
    private MenuExtMapper menuExtMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void contextLoads() {
        System.out.println("****************");
    }

    @Test
    public void test1() {
        ColorCrowedEnum colorCrowedEnum = busTypeCache.getColorCrowedEnum("比亚迪K10", 10L);
        System.out.println(colorCrowedEnum.getValue());
    }

    @Test
    public void pageTest() {
        IPage<BaseType> page = new Page<>(2, 3);
//        List<VisitorEncrypt> onlineDrugAuths = visitorEncryptMapper.selectLisTest(page);
        IPage<BaseType> onlineDrugAuths = baseTypeMapper.selectPageTest(page);
        List<BaseType> records = onlineDrugAuths.getRecords();
        System.out.println(JSON.toJSONString(records));
    }

    @Test
    public void menuExtUploadTest() throws Exception {

        String dir = System.getProperty("user.dir");
        String excelPath = dir+ File.separator+"/doc/菜单扩展.xlsx";
        List<MenuExtVO> menuExtList = ExcelUtil.readExcel2Bean(new FileInputStream(new File(excelPath)), MenuExtVO.class);
        System.out.println("menuExtList size: " + menuExtList.size());
        menuExtList.forEach(menuExtVO -> {
            MenuExt menuExt = new MenuExt();
            BeanUtils.copyProperties(menuExtVO, menuExt);
            menuExtMapper.insert(menuExt);
        });
        System.out.println("insert success!");
    }

    @Test
    public void menuExtTest() {
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().ne("menu_type", "module"));
        System.out.println("menus size: " + menus.size());
        List<MenuExt> menuExtList = new ArrayList<>();
        for (Menu menu : menus) {
            if(StringUtils.isNotEmpty(menu.getPermissionType())){
                String permissionType = menu.getPermissionType();
                String[] split = permissionType.split(",");
                for (String s : split) {
                    MenuExt menuExt = new MenuExt();
                    menuExt.setMenuId(menu.getId());
                    menuExt.setMenuName(menu.getMenuName());
                    menuExt.setPermissionType(s);
                    menuExt.setRemark(menu.getRemark());
                    menuExtList.add(menuExt);
                }
            }else {
                MenuExt menuExt = new MenuExt();
                menuExt.setMenuId(menu.getId());
                menuExt.setMenuName(menu.getMenuName());
                menuExt.setPermissionType("");
                menuExt.setRemark(menu.getRemark());
                menuExtList.add(menuExt);
            }
        }
        System.out.println("menuExtList size: " + menuExtList.size());
        menuExtList.forEach(menuExt -> {
            menuExtMapper.insert(menuExt);
        });
        System.out.println("insert success!");
    }

    @Test
    public void pinYinTest() throws Exception {


        List<User> users = userMapper.selectList(new QueryWrapper<User>());
        if(CollectionUtils.isNotEmpty(users)){
            for (User user : users) {
                //设置拼音字段
                setKeyWordField(user);
                userMapper.updateKeyWordById(user);
            }
        }
    }

    private void setKeyWordField(User user) {
        if(StringUtils.isEmpty(user.getRealName())){
            return;
        }

        if (PinYinUtils.containsChinese(user.getRealName())) {
            String originalConvert = PinYinUtils.convert(user.getRealName());
            user.setKeyWord(originalConvert );
        }else {
            user.setKeyWord(user.getRealName());
        }
    }

    /**
     * 非对称加密
     * @throws Exception
     */
    @Test
    public void RSATest() throws Exception {
        // 生成RSA密钥对
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();
        keygen.initialize(2048, random);
        KeyPair keyPair = keygen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // 将公钥发送给客户端
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("Public key: " + publicKeyString);

        // 客户端输入密码，并使用公钥进行加密
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter password: ");
        String password = reader.readLine();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedPassword = cipher.doFinal(password.getBytes());
        String encryptedPasswordString = Base64.getEncoder().encodeToString(encryptedPassword);
        System.out.println("Encrypted password: " + encryptedPasswordString);

        // 服务器使用私钥解密密码，并校验是否正确
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedPassword = cipher.doFinal(Base64.getDecoder().decode(encryptedPasswordString));
        String decryptedPasswordString = new String(decryptedPassword);
        System.out.println("Decrypted password: " + decryptedPasswordString);

        // 校验密码是否正确
        if (password.equals(decryptedPasswordString)) {
            System.out.println("Password is correct!");
        } else {
            System.out.println("Password is incorrect!");
        }
    }

}
