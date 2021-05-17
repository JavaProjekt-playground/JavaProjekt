using Project2MobileApp.Database;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;

namespace Project2MobileApp.ViewModels
{
    public class MainPageViewModel : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;

        private void OnPropertyChanged(string propName)
        {
            PropertyChanged.Invoke(this, new PropertyChangedEventArgs(propName));
        }

        public MainPageViewModel()
        {
            GetInfo();
        }

        private InfoBundle bundle;
        public InfoBundle Bundle
        {
            get => bundle;
            set
            {
                bundle = value;
                OnPropertyChanged(nameof(Bundle));
            }
        }

        private async void GetInfo()
        {
            Bundle = await App.DB.GetInfoBundle();
        }

    }
}
