package uz.ikhtidev.diskret.utils

enum class TYPE(val fileType:String, val typeName:String) {
    M("m", "Ma\'ruza"), //- ma'ruza
    V("v", "Muammoli misol va masalalar"), //- muammoli masala va topshiriq
    I("i", "Mustaqil ishlash uchun savollar"), //- mustaqil ishlash uchun savollar
    T("t", "Test sinovi"), //- test
    P("p", "Taqdimot"); //- prezentatsiya

    companion object {
        infix fun from(value: String): TYPE? = values().firstOrNull { it.fileType == value }
    }
}