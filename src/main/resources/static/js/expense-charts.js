document.addEventListener('DOMContentLoaded', function () {
  // Mobile menu toggle
  const mobileMenuButton = document.getElementById('mobile-menu-button');
  if (mobileMenuButton) {
    mobileMenuButton.addEventListener('click', function () {
      const mobileMenu = document.getElementById('mobile-menu');
      mobileMenu.classList.toggle('hidden');
    });
  }

  // Miesięczny wykres słupkowy z kategoriami
  const monthlyChartCanvas = document.getElementById('expenseMonthlyChart');
  if (monthlyChartCanvas) {
    const ctx = monthlyChartCanvas.getContext('2d');

    // Lepsze ustawienie rozdzielczości
    const dpr = window.devicePixelRatio || 1;
    const rect = monthlyChartCanvas.getBoundingClientRect();

    // Ustaw style CSS
    monthlyChartCanvas.style.width = rect.width + 'px';
    monthlyChartCanvas.style.height = rect.height + 'px';

    // Ustaw rzeczywiste wymiary canvas
    monthlyChartCanvas.width = rect.width * dpr;
    monthlyChartCanvas.height = rect.height * dpr;

    // Skaluj kontekst
    ctx.scale(dpr, dpr);

    // Zbierz dane z tabeli
    const expenseData = [];
    const rows = document.querySelectorAll('tbody tr');

    rows.forEach((row) => {
      const nameElement = row.querySelector(
        'td:nth-child(1) div:last-child div'
      );
      const amountElement = row.querySelector('td:nth-child(2) div');
      const categoryElement = row.querySelector(
        'td:nth-child(3) span:last-child'
      );
      const dateElement = row.querySelector('td:nth-child(4) span');

      if (nameElement && amountElement && categoryElement && dateElement) {
        const name = nameElement.textContent.trim();
        const amountText = amountElement.textContent
          .replace(' zł', '')
          .replace(',', '.');
        const amount = parseFloat(amountText);
        const category = categoryElement.textContent.trim();
        const dateText = dateElement.textContent.trim();

        // Przekonwertuj datę (dd.MM.yyyy) na obiekt Date
        const dateParts = dateText.split('.');
        const date = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]);

        expenseData.push({
          name: name,
          amount: amount,
          category: category,
          date: date,
        });
      }
    });

    // Grupuj dane według miesięcy i kategorii
    const monthlyData = {};
    const categories = new Set();

    expenseData.forEach((expense) => {
      const monthKey = `${expense.date.getFullYear()}-${String(
        expense.date.getMonth() + 1
      ).padStart(2, '0')}`;

      if (!monthlyData[monthKey]) {
        monthlyData[monthKey] = {};
      }

      if (!monthlyData[monthKey][expense.category]) {
        monthlyData[monthKey][expense.category] = 0;
      }

      monthlyData[monthKey][expense.category] += expense.amount;
      categories.add(expense.category);
    });

    // Sortuj miesiące i weź ostatnie 12
    const sortedMonths = Object.keys(monthlyData).sort();
    const last12Months = sortedMonths.slice(-12);
    const categoriesArray = Array.from(categories);

    // Lepsze, bardziej kontrastowe kolory
    const categoryColors = {
      Dom: '#DC2626', // Czerwony
      Transport: '#EA580C', // Pomarańczowy
      Jedzenie: '#16A34A', // Zielony
      Rozrywka: '#7C3AED', // Fioletowy
      Zdrowie: '#0891B2', // Niebieski
      Ubrania: '#DB2777', // Różowy
      Edukacja: '#65A30D', // Limonkowy
      Inne: '#6B7280', // Szary
      Samochód: '#F59E0B', // Żółty
    };

    // Więcej kontrastowych kolorów
    const colors = [
      '#DC2626', // Czerwony
      '#EA580C', // Pomarańczowy
      '#16A34A', // Zielony
      '#7C3AED', // Fioletowy
      '#0891B2', // Niebieski
      '#DB2777', // Różowy
      '#65A30D', // Limonkowy
      '#6B7280', // Szary
      '#F59E0B', // Żółty
      '#BE123C', // Ciemny różowy
      '#059669', // Morski
      '#7C2D12', // Brązowy
    ];

    categoriesArray.forEach((category, index) => {
      if (!categoryColors[category]) {
        categoryColors[category] = colors[index % colors.length];
      }
    });

    // Przygotuj datasets dla każdej kategorii
    const datasets = categoriesArray.map((category) => ({
      label: category,
      data: last12Months.map((month) => monthlyData[month][category] || 0),
      backgroundColor: categoryColors[category],
      borderColor: categoryColors[category],
      borderWidth: 0, // Usuń obramowanie
      borderRadius: 2, // Dodaj lekkie zaokrąglenie
    }));

    // Etykiety miesięcy
    const monthLabels = last12Months.map((month) => {
      const [year, monthNum] = month.split('-');
      const date = new Date(year, monthNum - 1);
      return date.toLocaleDateString('pl-PL', {
        month: 'short',
        year: 'numeric',
      });
    });

    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: monthLabels,
        datasets: datasets,
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        devicePixelRatio: dpr,
        animation: {
          duration: 1000,
          easing: 'easeInOutQuart',
        },
        scales: {
          x: {
            stacked: true,
            grid: {
              display: false,
            },
            ticks: {
              font: {
                size: 12,
                weight: '500',
              },
              color: '#374151',
            },
          },
          y: {
            stacked: true,
            beginAtZero: true,
            grid: {
              color: '#E5E7EB',
              lineWidth: 1,
            },
            ticks: {
              callback: function (value) {
                return value.toFixed(0) + ' zł';
              },
              font: {
                size: 12,
                weight: '500',
              },
              color: '#374151',
            },
          },
        },
        plugins: {
          legend: {
            position: 'bottom',
            labels: {
              padding: 20,
              usePointStyle: true,
              pointStyle: 'circle',
              font: {
                size: 13,
                weight: '500',
              },
              color: '#374151',
            },
          },
          tooltip: {
            backgroundColor: 'rgba(0, 0, 0, 0.8)',
            titleColor: '#fff',
            bodyColor: '#fff',
            borderColor: '#374151',
            borderWidth: 1,
            cornerRadius: 8,
            titleFont: {
              size: 14,
              weight: 'bold',
            },
            bodyFont: {
              size: 13,
            },
            callbacks: {
              title: function (context) {
                return context[0].label;
              },
              label: function (context) {
                return `${context.dataset.label}: ${context.parsed.y.toFixed(
                  2
                )} zł`;
              },
              footer: function (tooltipItems) {
                let total = 0;
                tooltipItems.forEach(function (tooltipItem) {
                  total += tooltipItem.parsed.y;
                });
                return `Suma: ${total.toFixed(2)} zł`;
              },
            },
          },
        },
        interaction: {
          mode: 'index',
          intersect: false,
        },
      },
    });
  }
});
