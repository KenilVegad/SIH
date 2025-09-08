
import { ChevronDown, Search, Facebook, Twitter, Instagram, Linkedin, Youtube } from 'lucide-react';
import Link from 'next/link';

export default function Home() {
  return (
    <div className="bg-white">
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
              <a href="#" className="hover:underline">Sign In/Sign Up</a>
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
                <li><a href="#" className="block px-4 py-3 bg-teal-600">Additional Services</a></li>
                <li><a href="#" className="block px-4 py-3 hover:bg-blue-800">Data & Reports</a></li>
                <li><a href="#" className="block px-4 py-3 hover:bg-blue-800">Help & FAQs</a></li>
                <li><a href="#" className="block px-4 py-3 hover:bg-blue-800">Contact Us</a></li>
                </ul>
            </div>
        </nav>
      </header>

      <main className="container mx-auto px-4 py-8">
        <div className="text-sm text-gray-600 mb-4">
          Home &gt; Additional Services &gt; E-Consultation
        </div>
        <h2 className="text-2xl font-bold text-blue-900 mb-6">E-Consultation</h2>
        
        <div className="grid md:grid-cols-2 gap-8 mb-8">
          <div className="bg-gray-100 p-8">
            <h3 className="text-xl font-bold text-blue-900 mb-4">The eConsultation Module</h3>
            <img src="https://www.mca.gov.in/content/dam/mca/images/e-consultation-banner.png" alt="eConsultation Banner"/>
          </div>
          <div className="bg-gray-100 p-8">
            <h3 className="text-xl font-bold text-blue-900 mb-4">What is E-Consultation?</h3>
            <p className="text-gray-700">
              E-Consultation is an online platform wherein, proposed amendments/draft legislations can be posted, and stakeholders/users can submit their comments and suggestions. Before notifying any crucial amendment or new legislation, MCA will publish the draft document on E-Consultation portal for public consultation and inviting inputs and suggestions of external stakeholders.
            </p>
            <Link href="/amendments-proposals" className="mt-4 inline-block bg-blue-600 text-white font-bold py-2 px-4 rounded hover:bg-blue-700 transition duration-300">
                View Amendments
            </Link>
          </div>
        </div>

        <div className="border-t pt-8">
          <h3 className="text-xl font-bold text-blue-900 mb-4">List of documents open for consultation</h3>
          <div className="grid md:grid-cols-2 gap-8">
            <div>
              <h4 className="font-bold text-gray-800 mb-4">Comments due soon</h4>
              <div className="space-y-2">
                <div className="flex justify-between items-center p-2 bg-gray-100 rounded">
                  <span>Today</span>
                  <span className="bg-blue-600 text-white px-3 py-1 rounded">0</span>
                </div>
                <div className="flex justify-between items-center p-2 bg-gray-100 rounded">
                  <span>Next 7 Days</span>
                  <span className="bg-blue-600 text-white px-3 py-1 rounded">0</span>
                </div>
                <div className="flex justify-between items-center p-2 bg-gray-100 rounded">
                  <span>Beyond 7 Days</span>
                  <span className="bg-blue-600 text-white px-3 py-1 rounded">0</span>
                </div>
              </div>
            </div>
            <div>
              <h4 className="font-bold text-gray-800 mb-4">Posted Recently</h4>
              <div className="space-y-2">
                <div className="flex justify-between items-center p-2 bg-gray-100 rounded">
                  <span>Today</span>
                  <span className="bg-blue-600 text-white px-3 py-1 rounded">0</span>
                </div>
                <div className="flex justify-between items-center p-2 bg-gray-100 rounded">
                  <span>Last 7 Days</span>
                  <span className="bg-blue-600 text-white px-3 py-1 rounded">0</span>
                </div>
                <div className="flex justify-between items-center p-2 bg-gray-100 rounded">
                  <span>Earlier</span>
                  <span className="bg-blue-600 text-white px-3 py-1 rounded">0</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>

      <footer className="bg-blue-900 text-white mt-12">
        <div className="container mx-auto px-4 py-8">
          <h3 className="text-xl font-bold mb-4">Quick Links</h3>
          <div className="grid md:grid-cols-4 gap-8 text-sm">
            <div>
              <ul className="space-y-2">
                <li><a href="#">PMO</a></li>
                <li><a href="#">NFCG</a></li>
                <li><a href="#">Trademarks Portal</a></li>
                <li><a href="#">MyGov.in</a></li>
                <li><a href="#">Website Policies</a></li>
                <li><a href="#">Institute of Cost Accountants of India</a></li>
                <li><a href="#">XBRL V3</a></li>
              </ul>
            </div>
            <div>
              <ul className="space-y-2">
                <li><a href="#">ICAI(CA)</a></li>
                <li><a href="#">RTI Online</a></li>
                <li><a href="#">Invest India</a></li>
                <li><a href="#">Principal Accounts Office</a></li>
                <li><a href="#">IEPFA Portal</a></li>
                <li><a href="#">NSE</a></li>
                <li><a href="#">About Us</a></li>
              </ul>
            </div>
            <div>
              <ul className="space-y-2">
                <li><a href="#">IRDA</a></li>
                <li><a href="#">SEBI</a></li>
                <li><a href="#">in.Registry</a></li>
                <li><a href="#">NVS Portal</a></li>
                <li><a href="#">RBI</a></li>
                <li><a href="#">Participate in the Fight Against Corruption</a></li>
                <li><a href="#">Help & FAQs</a></li>
              </ul>
            </div>
            <div>
              <ul className="space-y-2">
                <li><a href="#">BSE</a></li>
                <li><a href="#">ICSI(CS)</a></li>
                <li><a href="#">Public Grievance Portal</a></li>
                <li><a href="#">Latest News</a></li>
                <li><a href="#">Mobile App policy</a></li>
                <li><a href="#">Contact Us</a></li>
              </ul>
            </div>
          </div>
          <div className="border-t border-blue-800 mt-8 pt-4 text-center text-xs">
            <p>This site is owned by Ministry of Corporate Affairs.</p>
            <p>Last Updated 06/09/2023</p>
          </div>
          <div className="flex justify-center space-x-4 mt-4">
              <a href="#"><Facebook className="h-6 w-6" /></a>
              <a href="#"><Twitter className="h-6 w-6" /></a>
              <a href="#"><Instagram className="h-6 w-6" /></a>
              <a href="#"><Linkedin className="h-6 w-6" /></a>
              <a href="#"><Youtube className="h-6 w-6" /></a>
          </div>
        </div>
      </footer>
    </div>
  );
}
