package gamaerry.notas.datos

import gamaerry.notas.R

fun getNotas() = listOf(
    Nota(
        titulo = "Título 1",
        contenido = "Contenido de nota numero 1"
    ),
    Nota(
        titulo = "Título 2",
        contenido = "Contenido de nota numero 2.\n\n1\n2\n3",
        color = R.color.naranja
    ),
    Nota(
        titulo = "Título 3",
        contenido = "Contenido de nota 3, el cual resulta muy largo y produce un cambio en las dimensiones del contenedor.\n\nLorem Ipsum is simply dummy text of the printing and typesetting industry.\n\nLorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        color = R.color.morado
    ),
    Nota(
        titulo = "Título 4",
        contenido = "Contenido de nota numero 4",
        color = R.color.verde
    ),
    Nota(
        titulo = "El enamorado y la muerte",
        contenido = "Vete bajo la ventana donde bordaba y cosia, te hechare cordel de seda para que subas arriba. Si la seda no alcanzara, mis trenzas añadiría.\n\n1\n2\n3\n4",
        color = R.color.amarillo
    ),
)