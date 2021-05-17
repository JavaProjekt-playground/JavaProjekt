namespace Project2MobileApp.Database
{
    public struct InfoBundle
    {
        public bool IsSet { get; set; }

        public int UserCount { get; set; }
        public int PlayfieldCount { get; set; }
        public int ImageCount { get; set; }

        public double MaxScore { get; set; }
        public double MinScore { get; set; }
        public double AvgScore { get; set; }

    }
}