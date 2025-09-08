import { ChevronDown, Search, Facebook, Twitter, Instagram, Linkedin, Youtube } from 'lucide-react';

export default function Header() {
    return (
        <header>
        <div className="bg-blue-900 text-white">
          <div className="container mx-auto px-4 flex justify-between items-center py-2 text-sm">
            <div className="flex items-center space-x-4">
              <a href="#" className="hover:underline">Skip to Main Content</a>
              <a href="#" className="hover:underline">Sitemap</a>
            </div>
            <div className="flex items-center space-x-4">
              <a href="#" className="flex items-center hover:underline">Theme: Light <ChevronDown className="inline-block h-4 w-4 ml-1" /></a>
              <a href="#" className="hover:underline">Font Size <span className="text-lg">A+</span> <span className="text-sm">A-</span></a>
              <a href="#" className="flex items-center hover:underline">Language: English <ChevronDown className="inline-block h-4 w-4 ml-1" /></a>
              <a href="https://www.mca.gov.in/content/mca/global/en/foportal/fologin.html" target="_blank" rel="noopener noreferrer" className="hover:underline">Sign In/Sign Up</a>
            </div>
          </div>
        </div>
        <div className="border-b">
            <div className="container mx-auto px-4 flex justify-between items-center py-4">
                <div className="flex items-center">
                <img src="https://www.mca.gov.in/content/dam/mca/images/mca_gov_logo.png" alt="MCA Logo" className="h-16 mr-4"/>
                <div>
                    <h1 className="text-2xl font-bold text-blue-900">MINISTRY OF CORPORATE AFFAIRS</h1>
                    <p className="text-sm text-gray-600">GOVERNMENT OF INDIA</p>
                    <p className="text-sm">
                        <span className="text-yellow-500">REGULATOR</span> • <span className="text-green-500">INTEGRATOR</span> • <span className="text-red-500">FACILITATOR</span> • <span className="text-blue-500">EDUCATOR</span>
                    </p>
                </div>
                </div>
                <div className="relative">
                <input type="text" placeholder="Search" className="border rounded-full bg-gray-100 py-2 px-4 w-80"/>
                <Search className="absolute right-4 top-2.5 h-5 w-5 text-gray-400" />
                </div>
            </div>
        </div>
        <nav className="bg-blue-900 text-white">
            <div className="container mx-auto px-4">
                <ul className="flex justify-center">
                <li><a href="#" className="block px-4 py-3 hover:bg-blue-800">Home</a></li>
                <li><a href="#" className="block px-4 py-3 hover:bg-blue-800">About MCA</a></li>
                <li><a href="#" className="block px-4 py-3 hover:bg-blue-800">Acts & Rules</a></li>
                <li><a href="#" className="block px-4 py-3 hover:bg-blue-800">My Workspace</a></li>
                <li><a href="#" className="block px-4 py-3 hover:bg-blue-800">My Application</a></li>
                <li><a href="#" className="block px-4 py-3 hover:bg-blue-800">MCA Services</a></li>
                <li><a href="/amendments-proposals" className="block px-4 py-3 bg-teal-600">Amendments & Proposals</a></li>
                <li><a href="#" className="block px-4 py-3 hover:bg-blue-800">Data & Reports</a></li>
                <li><a href="#" className="block px-4 py-3 hover:bg-blue-800">Help & FAQs</a></li>
                <li><a href="#" className="block px-4 py-3 hover:bg-blue-800">Contact Us</a></li>
                </ul>
            </div>
        </nav>
      </header>
    )
}