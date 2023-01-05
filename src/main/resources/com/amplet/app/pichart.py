def get_color(i):
    if i % 2 == 0:
        return "#67d037"
    else:
        return "#e41515"

for i in range(200):
    print(".default-color" + str(i) + ".chart-pie {")
    print("    -fx-pie-color: " + get_color(i) + ";")
    print("}")
