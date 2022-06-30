package pro.fengjian;

public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSqlSession() {
        return new DefaultSqlSession(configuration);
    }
}
