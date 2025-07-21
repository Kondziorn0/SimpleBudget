document.addEventListener('DOMContentLoaded', function() {
    // Mobile menu toggle
    const mobileMenuButton = document.getElementById('mobile-menu-button');
    if (mobileMenuButton) {
        mobileMenuButton.addEventListener('click', function() {
            const mobileMenu = document.getElementById('mobile-menu');
            mobileMenu.classList.toggle('hidden');
        });
    }

    // Miesięczny wykres słupkowy z kategoriami dochodów
    const monthlyChartCanvas = document.getElementById('monthlyChart');
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
        const incomeData = [];
        const rows = document.querySelectorAll('tbody tr');
        
        rows.forEach(row => {
            const nameElement = row.querySelector('td:nth-child(1) div:last-child div');
            const amountElement = row.querySelector('td:nth-child(2) div');
            const categoryElement = row.querySelector('td:nth-child(3) span:last-child');
            const dateElement = row.querySelector('td:nth-child(4) span');
            
            if (nameElement && amountElement && categoryElement && dateElement) {
                const name = nameElement.textContent.trim();
                const amountText = amountElement.textContent.replace(' zł', '').replace(',', '.');
                const amount = parseFloat(amountText);
                const category = categoryElement.textContent.trim();
                const dateText = dateElement.textContent.trim();
                
                // Przekonwertuj datę (dd.MM.yyyy) na obiekt Date
                const dateParts = dateText.split('.');
                const date = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]);
                
                incomeData.push({
                    name: name,
                    amount: amount,
                    category: category,
                    date: date
                });
            }
        });

        // Grupuj dane według miesięcy i kategorii
        const monthlyData = {};
        const categories = new Set();
        
        incomeData.forEach(income => {
            const monthKey = `${income.date.getFullYear()}-${String(income.date.getMonth() + 1).padStart(2, '0')}`;
            
            if (!monthlyData[monthKey]) {
                monthlyData[monthKey] = {};
            }
            
            if (!monthlyData[monthKey][income.category]) {
                monthlyData[monthKey][income.category] = 0;
            }
            
            monthlyData[monthKey][income.category] += income.amount;
            categories.add(income.category);
        });

        // Sortuj miesiące i weź ostatnie 12
        const sortedMonths = Object.keys(monthlyData).sort();
        const last12Months = sortedMonths.slice(-12);
        const categoriesArray = Array.from(categories);

        // Kolory dla kategorii dochodów (zielone odcienie)
        const categoryColors = {
            'Praca': '#059669',           // Zielony
            'Freelancing': '#16A34A',     // Jasnozielony
            'Inwestycje': '#0891B2',      // Niebieski
            'Sprzedaż': '#65A30D',        // Limonkowy
            'Nagrody': '#0D9488',         // Teal
            'Inne': '#6B7280',            // Szary
            'Bonus': '#F59E0B',           // Żółty
            'Prezent': '#EC4899'          // Różowy
        };

        // Więcej kontrastowych kolorów (zielone spektrum)
        const colors = [
            '#059669', // Zielony
            '#16A34A', // Jasnozielony
            '#0891B2', // Niebieski
            '#65A30D', // Limonkowy
            '#0D9488', // Teal
            '#10B981', // Emerald
            '#06B6D4', // Cyan
            '#84CC16', // Lime
            '#22C55E', // Green
            '#14B8A6', // Teal
            '#0EA5E9', // Sky
            '#6366F1'  // Indigo
        ];

        categoriesArray.forEach((category, index) => {
            if (!categoryColors[category]) {
                categoryColors[category] = colors[index % colors.length];
            }
        });

        // Przygotuj datasets dla każdej kategorii
        const datasets = categoriesArray.map(category => ({
            label: category,
            data: last12Months.map(month => monthlyData[month][category] || 0),
            backgroundColor: categoryColors[category],
            borderColor: categoryColors[category],
            borderWidth: 0,
            borderRadius: 2
        }));

        // Etykiety miesięcy
        const monthLabels = last12Months.map(month => {
            const [year, monthNum] = month.split('-');
            const date = new Date(year, monthNum - 1);
            return date.toLocaleDateString('pl-PL', { month: 'short', year: 'numeric' });
        });

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: monthLabels,
                datasets: datasets
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                devicePixelRatio: dpr,
                animation: {
                    duration: 1000,
                    easing: 'easeInOutQuart'
                },
                scales: {
                    x: {
                        stacked: true,
                        grid: {
                            display: false
                        },
                        ticks: {
                            font: {
                                size: 12,
                                weight: '500'
                            },
                            color: '#374151'
                        }
                    },
                    y: {
                        stacked: true,
                        beginAtZero: true,
                        grid: {
                            color: '#E5E7EB',
                            lineWidth: 1
                        },
                        ticks: {
                            callback: function(value) {
                                return value.toFixed(0) + ' zł';
                            },
                            font: {
                                size: 12,
                                weight: '500'
                            },
                            color: '#374151'
                        }
                    }
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
                                weight: '500'
                            },
                            color: '#374151'
                        }
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
                            weight: 'bold'
                        },
                        bodyFont: {
                            size: 13
                        },
                        callbacks: {
                            title: function(context) {
                                return context[0].label;
                            },
                            label: function(context) {
                                return `${context.dataset.label}: ${context.parsed.y.toFixed(2)} zł`;
                            },
                            footer: function(tooltipItems) {
                                let total = 0;
                                tooltipItems.forEach(function(tooltipItem) {
                                    total += tooltipItem.parsed.y;
                                });
                                return `Suma: ${total.toFixed(2)} zł`;
                            }
                        }
                    }
                },
                interaction: {
                    mode: 'index',
                    intersect: false
                }
            }
        });
    }
});