using Project2MobileApp.ViewModels;
using System.ComponentModel;
using Xamarin.Forms;

namespace Project2MobileApp.Views
{
    public partial class ItemDetailPage : ContentPage
    {
        public ItemDetailPage()
        {
            InitializeComponent();
            BindingContext = new ItemDetailViewModel();
        }
    }
}